name=sp24-teacher
echo "开始sp24-teacher服务"
kill -9 `ps -ef | grep ${name}service.jar |grep -v grep| awk '{print $2}'`
echo "关闭服务结束..."
echo "sp24-teacher"
nohup java -Dloader.path=libs/ -jar ./${name}service.jar >./logsp24teacher.out 2>&1 &
tail -f ./logsp24teacher.out
echo "已启动sp24-teacher服务..."
