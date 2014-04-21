whereismypi
===========
by Nico Maas, 2014
www.nico-maas.de


Java Software Solution to find your embeeded Systems / Raspberry Pis faster in your network


Installation on Raspberry Pi:
1.) Download the WhereIsMyPi.jar to your Raspberry Pi, i.e.:
    wget -O /home/pi/WhereIsMyPi.jar https://github.com/nmaas87/whereismypi/blob/master/WhereIsMyPi.jar?raw=true
2.) Configure Autostart for Where Is My Pi on boot:
    - sudo vi /etc/rc.local
    - Add following command before "exit 0":
      java -jar /home/pi/WhereIsMyPi.jar server
    - Save and close file
3.) Restart your Pi:
    sudo reboot
    

Installation on PC:
Just start WhereIsMyPi via double klicking the jar File


Both guides assume that you already have Java 7 or higher installed on your RPi / PC
