import { IProduto } from 'app/shared/model/produto.model';

export interface IGrupo {
    id?: number;
    nome?: string;
    produtos?: IProduto[];
}

export class Grupo implements IGrupo {
    constructor(public id?: number, public nome?: string, public produtos?: IProduto[]) {}
}
