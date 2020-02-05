#include <SoftwareSerial.h>
#include <TinyGPS.h>//incluimos TinyGPS

//Create software serial object to communicate with SIM900
SoftwareSerial mySerial(40, 41); //SIM900 Tx & Rx is connected to Arduino #7 & #8
TinyGPS gps;//Declaramos el objeto gps
SoftwareSerial serialgps(50,51);//Declaramos el pin 50 Tx y 51 Rx

//Declaramos la variables para la obtención de datos
unsigned long chars;
unsigned short sentences, failed_checksum;
float latitude, longitude;

void setup()
{
  
  //Begin serial communication with Arduino and Arduino IDE (Serial Monitor)
  Serial.begin(9600);

  //Iniciamos el puerto serie del gps
  serialgps.begin(9600);//Iniciamos el puerto serie del gps

  //Begin serial communication with Arduino and SIM900
  mySerial.begin(9600);

  Serial.println("Initializing..."); 

}

void loop()
{
//////////////////////

    while(serialgps.available()) 
    {
      int c = serialgps.read();
     
      if(gps.encode(c))  
      {
        //float latitude, longitude;
        gps.f_get_position(&latitude, &longitude);
        Serial.print("Latitud ; Longitud: "); 

        
        // parse from latitud tipo float a str con 6 decimales
        char str_lat[21];
        dtostrf(latitude,8,6, str_lat);
        // parse from longitud tipo float a str con 6 decimales
        char str_lon[9];
        dtostrf(longitude, 8,6,str_lon);
        // concadeno str lon y lat para enviar el dato via gsm como un solo mensaje
        strcat( str_lat, ";"); // 1ra concadenacion
        strcat( str_lat,str_lon); // 2da concadenacion y ultima
        // muestro el resultado de la concadenacion
        Serial.println(str_lat);
        // retraso 5 segundo por impresion
        //
        delay(5000); 

/////////////////////////////////
        delay(10000);

        mySerial.println("AT"); //Handshaking with SIM900
        updateSerial();
      
        mySerial.println("AT+CMGF=1"); // Configuring TEXT mode
        updateSerial();
        mySerial.println("AT+CMGS=\"+593969618162\"");//change ZZ with country code and xxxxxxxxxxx with phone number to sms
        updateSerial();
        mySerial.print(str_lat); //text content
        updateSerial();
        mySerial.write(26);
        
/////////////////////////////////
        // npi
        gps.stats(&chars, &sentences, &failed_checksum);  // npi
      }
  }

/////////////////////

  

}

void updateSerial()
{
  delay(500);
  while (Serial.available()) 
  {
    mySerial.write(Serial.read());//Forward what Serial received to Software Serial Port
  }
  while(mySerial.available()) 
  {
    Serial.write(mySerial.read());//Forward what Software Serial received to Serial Port
  }
}
