import { Moment } from 'moment';
import { ISaida } from 'app/shared/model/saida.model';
import { ILote } from 'app/shared/model/lote.model';
import { IApresentacao } from 'app/shared/model/apresentacao.model';
import { IGrupo } from 'app/shared/model/grupo.model';

export interface IProduto {
    id?: number;
    cdProduto?: number;
    nmProduto?: string;
    cstCompra?: number;
    cstVerder?: number;
    dtVencimento?: Moment;
    saidas?: ISaida[];
    ltoes?: ILote[];
    apresentacao?: IApresentacao;
    grupo?: IGrupo;
}

export class Produto implements IProduto {
    constructor(
        public id?: number,
        public cdProduto?: number,
        public nmProduto?: string,
        public cstCompra?: number,
        public cstVerder?: number,
        public dtVencimento?: Moment,
        public saidas?: ISaida[],
        public ltoes?: ILote[],
        public apresentacao?: IApresentacao,
        public grupo?: IGrupo
    ) {}
}
