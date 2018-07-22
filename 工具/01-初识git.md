# 初识Git

专栏推荐
> 廖雪峰老师的Git教程：[戳我](https://www.liaoxuefeng.com/wiki/0013739516305929606dd18361248578c67b8067c8c017b000)

> GitHub上的优秀中文教程：[戳我](https://github.com/geeeeeeeeek/git-recipes)

#### 读完本篇，你能获得啥？
- git的操作过程
- 常用的git命令操作

#### git的操作过程
![image](https://images0.cnblogs.com/blog2015/687225/201508/211915189723300.jpg)
git有这4个区域空间的概念，假设我们本地已经有一个git库了，然后我们在【工作区间】进行操作，这时使用命令，git status发现已经有变化了，然后我们要把更改后的文件添加到【缓存区】使用命令git add,再次使用git status命令，这时【工作区间】是干净的。目前为止我们的文件只是添加到了【缓存区】，继续使用命令git commit将缓存区的文件提交到【本地仓库】中，文件提交到这里，相当于你已经完成了一次文件的操作，但仅仅只是在本地可见，其它与你协作的小伙伴是看不见的。最后使用git push命令，将【本地仓库】中的文件推送到【远程仓库】中，这个仓库是大家可见的。


#### 列举下我常用的高频git命令：
- git status
> 查看当前分支状态
- git pull origin branch_name		
> 将远程库更新到本地
- git push origin branch_name		
 >  将本地分支推送到远程
- git log						
> 查看所有提交日志
- git log -n					
> 查看最近n次的提交记录
- git log --after="2014-7-1" --before="2014-7-4"
> 查看这个时间区间内的提交
- git log --author="John"		
> 查看该作者提交记录
- git add -A					
> 将所有更改的文件添加到存储区
- git commit -m"注释说明"		
> 将存储区的提交并写上提交注释
- git checkout branch_name			
> 切换到某个分支
- git branch					
> 查看当前分支
- git branch branch_name				
> 创建一个新的分支
- git merge  branch_name		
> 将该分支合并到当前所在的分支
- git branch -d branch_name	
> 删除一个分支	
- git commit -a -m'注释说明'	
> 相当于git add 和git commit的合体	
- git revert commit号码
> 撤销一个已经提交了的快照

### 案例：将代码库中的项目添加新功能
```
git branch dev      //先创建一个dev分支
git checkout dev    //切换到dev分支
git add -A          //在dev分支做了好多功能，全部提交
git commit -m'      //在dev分支做了好多功' 提交并写上注释
git push origin     //推到远程仓库
git checkout margin //切回主分支
git merge dev       //合并刚才的dev分支
git push origin     //再把master合并dev后的代码推送到远程端（这里不需要git commit操作，因为是线上合并的，没有在本地操作，你也可以git status查看，会发现是本地是干净的）

```

### 案例：将本地项目添加到代码库
> 其实主要还是要理解git的思想，这些步骤就很容易操作，而且出错了也知道怎么改，记住：先pull，在push
```
1.it init                           //在一个项目中初始化，会产生.git文件，默认是隐藏的
2.git add 文件名                    //将项目中的文件加到存储区
3.git status                       //可以查看下状态（可省略）
4.git commit -m '注释'             //提交存储区的文件
5.git remote add origin 仓库地址   //将本地关联到远程仓库
git pull origin                   //将远程文件拉下来，重复2-4步骤（如果没有可省略）
git push -u origin                   //推到远程仓库，-u第一次提交要加，以后就不要加了

```
### 案例：将A分支合并到B分支
> 熟悉常见的几个命令即可
```
1.git branch B                         //如果没有B分支就要先创建一个分支，如果有的话就可以直接切换到该分支git checkout B
2.git merge origin/A                 //将远程A的代码合并到当前分支中（也就是B分支）
3.可以

```
#### 关于冲突
> 协同开发经常会遇到冲突，但一般来说看git的提示都可以解决问题（一定要看提示），该删的删掉，该备份的先备份