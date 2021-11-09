name=sp24-teachersub
echo "开始sp24-teachersub服务"
kill -9 `ps -ef | grep ${name}service.jar |grep -v grep| awk '{print $2}'`
echo "关闭服务结束..."
echo "sp24-teachersub"
nohup java -Dloader.path=libs/ -jar ./${name}service.jar >./logsp24-teachersub.out 2>&1 &
tail -f ./logsp24-teachersub.out
echo "已启动sp24-teacher服务..."
