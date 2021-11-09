Quick setup — if you’ve done this kind of thing before
or	
git@github.com:zhangtingfeng/test.git
Get started by creating a new file or uploading an existing file. We recommend every repository include a README, LICENSE, and .gitignore.

…or create a new repository on the command line
echo "# test" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin git@github.com:zhangtingfeng/test.git
git push -u origin main
…or push an existing repository from the command line
git remote add origin git@github.com:zhangtingfeng/test.git
git branch -M main
git push -u origin main
…or import code from another repository
You can initialize this repository with code from a Subversion, Mercurial, or TFS project.



Git pull 强制拉取并覆盖本地代码

很吵请安青争 2018-09-23 12:01:19  91425  收藏 42
分类专栏： 研发管理工具 文章标签： Git
版权
两个电脑同时对git上的项目进行跟新时，不免要用到将git上的代码拉取到本地更新本地代码的操作，鉴于自己对git使用的还不是很熟练，所以就直接采取暴力的方法，直接拉取并覆盖本地的所有代码，命令如下

git fetch --all
git reset --hard origin/main
git pull


git fetch --all;git reset --hard origin/main;git pull
————————————————
版权声明：本文为CSDN博主「很吵请安青争」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/dpengwang/article/details/82821203





git强制推送命令

git push -f origin main

注释： origin远程仓库名，master分支名，-f为force，意为：强行、强制。

这行命令的意思就是强制用本地的代码去覆盖掉远程仓库的代码，敲git push --help可查看官方的解释（英文的）。当然不止这一种操作方式了，但是这种操作是最快(bao)速(li)的，不会有冲突什么的，当然我也有一个忠告：请谨慎使用！请谨慎使用！请谨慎使用！

echo "# gitworks202101SpringClound" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin git@github.com:zhangtingfeng/gitworks202101SpringClound.git
git push -u origin main



echo "# YunTestKnowledge" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin git@github.com:zhangtingfeng/YunTestKnowledge.git
git push -u origin main