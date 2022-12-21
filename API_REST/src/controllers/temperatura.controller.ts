import {Response,Request} from 'express'
import {Temperatura} from '../models/temperatura.model'
import moment from 'moment'

export class TemperaturaController{
  async getAll(req:Request, res:Response){
    try {
      const data = (await Temperatura.findAll())
        .reverse()
        .map(x =>{
          x.dataValues.createdAt = moment(x.dataValues.createdAt).utc().subtract(5,'hour').format('DD-MM-yyyy HH:mm:ss')
          return x.dataValues
        });
      res.json(data)
    } catch (error: any) {
      res.status(400).json({
        message:error.message ?? 'Error en el servidor'
      })
    }
  }
}