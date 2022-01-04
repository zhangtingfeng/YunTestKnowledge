name=sp06-ribbon
echo "开始sp06-ribbon服务"
kill -9 `ps -ef | grep ${name}service.jar |grep -v grep| awk '{print $2}'`
echo "关闭服务结束..."
echo "sp06-ribbon"
nohup java -Dloader.path=libs/ -jar ./${name}service.jar --spring.redis.ip=127.0.0.1 --spring.redis.host=127.0.0.1  --spring.redis.timeout=50000ms >./logsp06ribbon.out 2>&1 &
tail -f ./logsp06ribbon.out
echo "已启动sp06ribbon服务..."
