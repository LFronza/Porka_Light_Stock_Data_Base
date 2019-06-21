import { Moment } from 'moment';
import { IProduto } from 'app/shared/model/produto.model';

export interface ISaida {
    id?: number;
    dtSaida?: Moment;
    qtSaida?: number;
    produto?: IProduto;
}

export class Saida implements ISaida {
    constructor(public id?: number, public dtSaida?: Moment, public qtSaida?: number, public produto?: IProduto) {}
}
