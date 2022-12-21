import {Router} from 'express';
import {TemperaturaController} from '../controllers/temperatura.controller'

const router = Router();
const controller = new TemperaturaController()

router.get('/', controller.getAll);

export default router;