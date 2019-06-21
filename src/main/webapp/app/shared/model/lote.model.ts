import { Moment } from 'moment';
import { IFornecedor } from 'app/shared/model/fornecedor.model';
import { IProduto } from 'app/shared/model/produto.model';

export interface ILote {
    id?: number;
    dtEntrada?: Moment;
    qtEntrada?: number;
    dtVencimento?: Moment;
    nrLote?: number;
    fornecedor?: IFornecedor;
    produto?: IProduto;
}

export class Lote implements ILote {
    constructor(
        public id?: number,
        public dtEntrada?: Moment,
        public qtEntrada?: number,
        public dtVencimento?: Moment,
        public nrLote?: number,
        public fornecedor?: IFornecedor,
        public produto?: IProduto
    ) {}
}
