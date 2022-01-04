name=sp11-crawlnew
echo "开始${name}服务"
ID=`lsof -i:48016 |grep java |awk '{print $2}'`
echo $ID
kill  $ID
echo "关闭服务结束..."