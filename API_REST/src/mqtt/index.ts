import {connect, MqttClient} from 'mqtt';
import {Temperatura} from '../models/temperatura.model'

export class ClienteMQTT{
  private client:MqttClient;
  private broker:string = 'mqtt://test.mosquitto.org';
  private port:number = 1883;

  constructor(){
    this.client = connect(this.broker,{port:this.port});
  }

  conectar(){    
    this.client.on('connect', ()=>{
      console.log(`Conectado al broker -> ${this.broker}`);
      this.suscribir();
    });

    this.escucharMensajes();
  }

  private suscribir(){
    this.client.subscribe('esp32/temperatura');
  }

  private escucharMensajes(){
    this.client.on('message', async(topic:string,payload:Buffer)=>{

      await Temperatura.create({dato:parseFloat(payload.toString())})
      console.log(`${topic} -> ${payload}`);
    });
  }
}

