cls


							title sector 2
d:
cd D:\NetBeansProjects\SD_Final_Project\projecto\NodeGest\dist
start java -jar NodeGest.jar 127.0.0.1 1115 1111 2

							title zone 1 sector 2
cd D:\NetBeansProjects\SD_Final_Project\projecto\Node\dist
start java -jar Node.jar 127.0.0.1 1116 1115 1
cd D:\NetBeansProjects\SD_Final_Project\projecto\Sensor\dist
start java -jar Sensor.jar 127.0.0.1 1116 Temp
start java -jar Sensor.jar 127.0.0.1 1116 Humi
start java -jar Sensor.jar 127.0.0.1 1116 Radi


							title zone 2 sector 2
cd D:\NetBeansProjects\SD_Final_Project\projecto\Node\dist
start java -jar Node.jar 127.0.0.1 1117 1115 2
cd D:\NetBeansProjects\SD_Final_Project\projecto\Sensor\dist
start java -jar Sensor.jar 127.0.0.1 1117 Temp
start java -jar Sensor.jar 127.0.0.1 1117 Humi
start java -jar Sensor.jar 127.0.0.1 1117 Radi