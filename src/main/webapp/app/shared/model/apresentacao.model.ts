import { IProduto } from 'app/shared/model/produto.model';

export interface IApresentacao {
    id?: number;
    nome?: string;
    produtos?: IProduto[];
}

export class Apresentacao implements IApresentacao {
    constructor(public id?: number, public nome?: string, public produtos?: IProduto[]) {}
}
