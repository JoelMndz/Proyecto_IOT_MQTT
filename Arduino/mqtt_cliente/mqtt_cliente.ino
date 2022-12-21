#include <PubSubClient.h>
#include <DHTesp.h>
#include <WiFi.h>

//Variables globales
const char* ssid = "Redmi";
const char* password = "joel2021";
const char* mqttServer = "test.mosquitto.org";
const int mqttPort = 1883;
String temperatura;

WiFiClient espClient;
PubSubClient client(espClient);
long tiempoAnterior = 0;
int DHTPIN = 23;

DHTesp dht;

//Metodos
void conectarWifi();
void iniciarDHT();
void conectarBroker();


void setup() {
  Serial.begin(9600);
  conectarWifi();
  iniciarDHT();
  client.setServer(mqttServer, mqttPort);
}

void loop() {
  if (WiFi.status() != WL_CONNECTED) {
    conectarWifi();
  }

  if(!client.connected()){
    conectarBroker();
  }
  client.loop();

  long tiempoActual = millis();
  if((tiempoActual - tiempoAnterior) > (1000 * 60)){
    tiempoAnterior = tiempoActual;
    Serial.print("Temperatura: ");
    temperatura = String(dht.getTemperature());
    Serial.println(temperatura);
    
    client.publish("esp32/temperatura",temperatura.c_str());
  }
}

void iniciarDHT(){
  dht.setup(DHTPIN, DHTesp::DHT11);
}

void conectarWifi(){
  WiFi.begin(ssid, password);
  Serial.print("Conectando Wifi");
  while(WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Conectado al WIFI su IP es: ");
  Serial.println(WiFi.localIP());
}

void conectarBroker(){
  while(!client.connected()){
    Serial.println("Conectando Broker MQTT...");
    if(client.connect("arduinoClient")){
      Serial.println("Broker conectado!");
      break;
    }else{
      Serial.print("fallo, rc=");
      Serial.print(client.state());
      Serial.println("Volveremos a intentarlo en 5 segundos");
      delay(5000);
    }
    delay(500);    
  }
}
