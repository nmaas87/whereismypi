whereismypi	![alt text](https://raw.githubusercontent.com/nmaas87/whereismypi/master/src/wmp.png "Where Is My Pi Logo") 
===========
by Nico Maas, 2014

www.nico-maas.de


Java Software Solution to find your embeeded Systems / Raspberry Pis faster in your network



Installation on Raspberry Pi:

1.) Download the WhereIsMyPi.jar to your Raspberry Pi, i.e.:

    curl -L https://github.com/nmaas87/whereismypi/blob/master/WhereIsMyPi.jar?raw=true > /home/pi/WhereIsMyPi.jar
    
    
2.) Configure Autostart for Where Is My Pi on boot:

    sudo vi /etc/rc.local
    
    Add following command before the line "exit 0":
    
    java -jar /home/pi/WhereIsMyPi.jar server

    Save and close file


3.) Restart your Pi:

    sudo reboot
    

Installation on PC:

Just start WhereIsMyPi via double klicking the jar File



Both guides assume that you already have Java 7 or higher installed on your RPi / PC






Addititonal Information:

Build Where Is My Pi from scratch:

	  Download the src Folder from Github
	  cd src
	  javac *.java


Start Where is My Pi on RPi:

		
		java DiscoverySystem server




Start Where Is My Pi on PC:


		java DiscoverySystem


