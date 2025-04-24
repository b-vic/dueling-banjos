for i in $(seq 1 10);
do
    echo "Warm up: " $i
    curl http://localhost:8080/webclientasync && echo ""
    curl http://localhost:8080/webclientsync && echo ""
    curl http://localhost:8080/restclient && echo ""
    curl http://localhost:8080/resttemplate && echo ""
done

