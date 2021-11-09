name=sp21-tools
echo "瀵拷婵獱p21-tools閺堝秴濮�"
kill -9 $(ps -ef | grep ${name}service.jar | grep -v grep | awk '{print $2}')
echo "閸忔娊妫撮張宥呭缂佹挻娼�..."
echo "sp21-tools"
nohup java -Dloader.path=libs/ -jar ./${name}service.jar --myupload.serverIMGUpload=/www/StaticImg/edu --eureka.client.service-url.defaultZone=http://localhost:48011/eureka >./sp21-toolslog.out 2>&1 &
tail -f ./sp21-toolslog.out
echo "瀹告彃鎯庨崝鈺痯21-tools閺堝秴濮�...sp21-toolslog.out"
