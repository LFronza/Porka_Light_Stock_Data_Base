import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApresentacao } from 'app/shared/model/apresentacao.model';

@Component({
    selector: 'jhi-apresentacao-detail',
    templateUrl: './apresentacao-detail.component.html'
})
export class ApresentacaoDetailComponent implements OnInit {
    apresentacao: IApresentacao;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ apresentacao }) => {
            this.apresentacao = apresentacao;
        });
    }

    previousState() {
        window.history.back();
    }
}
