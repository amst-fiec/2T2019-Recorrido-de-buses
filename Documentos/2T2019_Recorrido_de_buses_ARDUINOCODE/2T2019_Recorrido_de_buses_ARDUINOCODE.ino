#include <SoftwareSerial.h>
#include <TinyGPS.h>

TinyGPS gps;

// se eligen los puerto; pin 50 Tx 51 Rx; para conectar el modulo gps
SoftwareSerial serialgps(50,51);

unsigned long chars;
unsigned short sentences, failed_checksum;

// se define la estructura para el payload a sigfox
struct gpscoord {
  float a_latitude;  // 4 bytes
  float a_longitude; // 4 bytes
};

void setup() {
  Serial.begin(9600);
  //inicializo el puerto serial del modulo gps
  serialgps.begin(9600);
  Serial.println("");
  Serial.println(" --- Buscando Señal --- ");
  Serial.println("");

}

void loop() {
  
  while(serialgps.available()){
    
    int c=serialgps.read();
      
      if (gps.encode(c)){
        
        float latitude,longitude;
        gps.f_get_position(&latitude,&longitude);
        
        //arduino obtien las coordenadas
        gpscoord coords = {latitude, longitude};// se pasa los datos a la estructura

        //inicializa metodo "sigfoxSend" para enviar datos por SigFox
        bool answer = sigfoxSend(&coords, sizeof(gpscoord));//// enviamos por sigfox
    
        
        Serial.print('latitud: ');
        Serial.print(latitude,5);
        Serial.print('\tlongiutd: ');
        Serial.println(longitude,5);
        
        gps.stats(&chars,&sentences,&failed_checksum);

        delay(100);
        }
    }
}

// se crea metodo para que convierte datos de longitud y latiitud en exadecimal
// y envia datos por SigFox a su plataforma
bool sigfoxSend(const void* data, uint8_t len) {
  uint8_t* bytes = (uint8_t*)data;
  serialgps.println("AT$RC");
  serialgps.println("AT$SF=");
  for(uint8_t i = len-1; i < len; --i) {
    if (bytes[i] < 16) {serialgps.print("0");}
    serialgps.print(bytes[i], HEX);
  }
  serialgps.print('\r');
  }
