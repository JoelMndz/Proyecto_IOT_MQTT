import {DataTypes} from 'sequelize';
import {sequelize} from '../database'

export const Temperatura = sequelize.define('Temperatura',{
  dato: DataTypes.INTEGER,
})