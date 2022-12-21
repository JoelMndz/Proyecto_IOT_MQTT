import express,{ Request, Response } from "express"
import morgan from "morgan"
import cors from "cors"

import {PORT} from './config'
import {conectar} from  './database'
import {ClienteMQTT} from './mqtt'

import TemperaturaRoute from './routes/temperatura.route'
import UsuarioRoute from './routes/usuario.route'
import { Usuario } from "./models/usuario.model"

export class Server{
  private app;
  private clientMQTT:ClienteMQTT;

  constructor(){
    this.app = express()
    this.configuration()
    this.middlewares()
    this.routes()
    this.clientMQTT = new ClienteMQTT()
  }

  private routes() {
    this.app.get('/',(req:Request, res:Response)=>{
      res.json({
        name:'REST API - IOT'
      })
    });

    this.app.use('/api/temperatura', TemperaturaRoute);
    this.app.use('/api/login',UsuarioRoute)
  }
  
  private middlewares() {
    this.app.use(cors())
    this.app.use(morgan('dev'))
    this.app.use(express.json())
    this.app.use(express.urlencoded({extended:false}))
  }

  private configuration() {
    this.app.set('port',PORT || 5000)
    
  }
  
  async listen(){
    this.app.listen(this.app.get('port'),()=>{
      console.log(`Server corriendo en el puerto ${this.app.get('port')}`); 
    });
    await conectar();
    await this.crearAdministrador();
    this.clientMQTT.conectar();
  }

  private async crearAdministrador(){
    try {
      const data = await Usuario.findOne({
        where:{
          email:'administrador@gmail.com',
          password: '12345678'
        }
      })
      if(!data){
        await Usuario.create({
          nombre: 'Admin',
          email:'administrador@gmail.com',
          password: '12345678'
        })
        console.log('Usuario administrador creado!');        
      }
    } catch (error) {
      console.log(error);      
    }
  }

}