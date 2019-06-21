import { ILote } from 'app/shared/model/lote.model';

export interface IFornecedor {
    id?: number;
    nmFornecedor?: string;
    endereco?: string;
    cidade?: string;
    cnpj?: number;
    telefone?: number;
    lotes?: ILote[];
}

export class Fornecedor implements IFornecedor {
    constructor(
        public id?: number,
        public nmFornecedor?: string,
        public endereco?: string,
        public cidade?: string,
        public cnpj?: number,
        public telefone?: number,
        public lotes?: ILote[]
    ) {}
}
