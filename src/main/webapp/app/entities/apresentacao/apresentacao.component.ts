import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IApresentacao } from 'app/shared/model/apresentacao.model';
import { AccountService } from 'app/core';
import { ApresentacaoService } from './apresentacao.service';

@Component({
    selector: 'jhi-apresentacao',
    templateUrl: './apresentacao.component.html'
})
export class ApresentacaoComponent implements OnInit, OnDestroy {
    apresentacaos: IApresentacao[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected apresentacaoService: ApresentacaoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.apresentacaoService
            .query()
            .pipe(
                filter((res: HttpResponse<IApresentacao[]>) => res.ok),
                map((res: HttpResponse<IApresentacao[]>) => res.body)
            )
            .subscribe(
                (res: IApresentacao[]) => {
                    this.apresentacaos = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInApresentacaos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IApresentacao) {
        return item.id;
    }

    registerChangeInApresentacaos() {
        this.eventSubscriber = this.eventManager.subscribe('apresentacaoListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
