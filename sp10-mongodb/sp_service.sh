name=sp10-Mongodb
echo "开始${name}服务"
kill -9 `ps -ef | grep ${name}service.jar |grep -v grep| awk '{print $2}'`
echo "关闭服务结束..."
echo ${name}
nohup java -Dloader.path=libs/ -jar ./${name}service.jar --spring.data.mongodb.host=127.0.0.1 >./${name}log.out 2>&1 &
tail -f ./${name}log.out
echo "已启动${name}服务...${name}log.out"
