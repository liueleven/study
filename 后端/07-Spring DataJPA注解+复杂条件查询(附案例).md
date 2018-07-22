# JPA注解、复杂条件查询

更新于2018-07-22

> 最近SpringBoot+Spring Data Jpa好火的样子，公司一年前就已经开始使用Spring全家桶了，下面记录下自己的一些小心得

## 常用注解

> 下列是一些常用的注解，以及它们功能的简短说明
### 表级别注解

常用的表注解有：
- @Entity   
    > 该注解放在类上，会在数据库创建相应的表，如果不指定表名，会以类名单词间用下划线来命名

- @Table 
    > 该注解放在类上，用来声明和数据库中哪个表对应，如果不指定表名，会以类名单词间用下划线来命名

- @Index 
    > 该注解放在类上，如果你要为某个字段创建索引，就可以使用这个注解

### 字段级别注解
- @Id
    > 该注解放在字段上，用来指定表中的主键id
- @GeneratedValue
    > 该注解放在字段主键id上，用来指定id的增长策略
- @Column
    > 该注解放在字段上，用来声明字段的一些属性
- @Enumerated
    > 该注解放在字段上，用来声明枚举类型字段
- @Transient
    > 该注解放在字段上，表示在数据库表中不会生成该字段

#### 表之间的注解

- @ManyToOne
    > 该注解放在字段上，表示该表和该字段的关系是多对一

- @ManyToMany
    > 该注解放在字段上，表示该表和该字段的关系是多对多

- @OneToMany
    > 该注解放在字段上，表示该表和该字段的关系是一对多

- @OneToOne
    > 该注解放在字段上，表示该表和该字段的关系是一对一
- @JoinTable
    > 该注解放在字段上，表示添加一个表 
- @JoinColumn
    > 该注解放在字段上，表示添加一列 

#### 级联关系
> 多表之间的关联肯定要用到级联关系的，主要是cascade = CascadeType.*
- ALL
- PERSIST
- MERGE
- REMOVE
- REFRESH
- DETACH
> 上面的这些一般常用的是ALL，MERGE,其实它相对应的是CRUD，通过单词就知道具体功能了，这里一般遇到的问题会比较多，级联操作中像什么无法删除，保存不了，更新不了，就要根据具体的业务逻辑进行使用灵活更改了


## 案例代码

user表
```
@Entity
@Table(name = "user")
public class User {

    //定义了一个枚举类型
    public enum Role {
		CUSTOMER, ADMIN
	}
	
	//声明该字段是主键
	//主键生成策略，这里是自增
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//为这个字段设置属性：该字段不更新，不能为空，是唯一的，可以插入，长度为11
	@Column(updatable = false, nullable = false, unique = true, insertable = true, length = 11)
	private String phone;
	
	//默认是0，该字段不能为空
	@Column(columnDefinition = "INT DEFAULT 0", nullable = false)
	private Boolean enable;
	
	//枚举属性，在数据库中以字符串保存
	@Enumerated(EnumType.STRING)
	private Role role;
	
	//一对多
	@OneToMany
	private Address address;
	
	//...getter/setter
	
}
```
team表和user表；游戏中，一个人参加了很多个队，一个队也可以在很多人，多对多关系场景
```
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    ... //省略若干字段
    
    //多对多,级联刷新
	//这里多对多关系，建一个中间表来维护，这个表的名称是ref_team_user,它
	//有两个字段，名称分别是ref_team_id和ref_user_id
	@ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "ref_team_user", joinColumns = { @JoinColumn(name = "ref_team_id") }, inverseJoinColumns = { @JoinColumn(name = "ref_user_id") })
    private List<User> users;
    
    //...getter/setter
}
```

## Jpa分页查询
> 该案例是用SpringBoot中完成的
```
//controller层
/**
 *@ApiOperation：是swagger注解
 *sort = { "id" }: 按id进行排序
 *direction = Sort.Direction.DESC：降序
 *page,size: 默认是第一页，每页20条数据
 */
@ApiOperation(value = "条件分页查询", notes = "条件分页查询")
@RequestMapping(value = "/page", method = RequestMethod.GET)
@ResponseBody
public Response<Page<T>> findByPage(
    @PageableDefault(sort = { "id" }, page = 0, size = 20, 
    direction = Sort.Direction.DESC) Pageable pageable) {
	return Response.success(service.findByPage( pageable));
}
//server层
/**
 *看起来很简单，jpa会自动帮转换处理的
 */
public Page<User> findByPage(Pageable pageable) {
	return repository.findAll(pageable);
}
```
## Jpa多条件复杂查询分页处理
> 主要是利用了Specification这个接口，下面有一个唯一方法：    
> Predicate toPredicate(Root<T> var1, CriteriaQuery<?> var2, CriteriaBuilder var3);

注意它里面三个关键参数：
- Root  : 实体对象
- CriteriaQuery： 提供了查询的一些方法
- CriteriaBuilder：构建查询
##### 功能：按用户地址(模糊查询)分页查询
```
//service层
public Page<User> findByPage(User example, Pageable pageable) {
		return repository.findAll(new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<Competition> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if(example.getAddress()!=null){
				    //cb.*,几乎有所有的查询功能，root是获得一个字段，address是前端传进来的一个地址
					predicates.add(cb.like(root.get("address"), "%address%));
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, pageable);
	}
```
##### 功能： 一张表A关联了另一张表B（外键），现在需要根据bId找到所有记录
```
public Page<Competition> findByPage(Competition example, Pageable pageable) {
		//Sort 进行排序
		logger.info("==========>当前页数：{}，每页显示数量：{}",pageable.getPageNumber(),pageable.getPageSize());
		//根据id，和是否热门进行排序
		Sort sort = new Sort(Sort.Direction.DESC,"isHot")
				.and(new Sort(Sort.Direction.DESC,"id"));
		pageable = new PageRequest(pageable.getPageNumber(),pageable.getPageSize(),sort);
		return repository.findAll(new Specification<Competition>() {
			@Override
			public Predicate toPredicate(Root<Competition> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if(example != null){
					if(example.getMatches().getId() != null) {
						Long id = example.getMatches().getId();
						//这里是join了另一张表
						predicates.add(cb.equal(root.join("matches").get("id").as(Long.class),id));
					}
				}

				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}, pageable);
	}
```


## 还有一种复杂情况（分页）
> 表CompetitionTeam和表Competition多对多关系，现在知道了Competition中的id，然后查找出所有CompetitionTeam,考虑到业务逻辑，还需分页处理

直接上车，看代码
```
//repository
@Query("SELECT cts from Competition c  join c.competitionTeams cts where c = ?1")
List<CompetitionTeam>  findTeamsByPage(Competition competition, Pageable pageable);

//domain -> Competition实体类
@ApiModelProperty(value = "参赛队数")
@ManyToMany(cascade = CascadeType.PERSIST)
@JoinTable(name = "ref_competition_team", joinColumns = { @JoinColumn(name = "ref_competition_id") }, inverseJoinColumns = { @JoinColumn(name = "ref_team_id") })
private List<CompetitionTeam> competitionTeams;
```
笔记：这条sql是老大教我写的，简直了，大开眼界，还可以这样写，太棒了！对jpa的灵活更加的佩服，全身投地了！