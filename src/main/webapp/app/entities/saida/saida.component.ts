import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISaida } from 'app/shared/model/saida.model';
import { AccountService } from 'app/core';
import { SaidaService } from './saida.service';

@Component({
    selector: 'jhi-saida',
    templateUrl: './saida.component.html'
})
export class SaidaComponent implements OnInit, OnDestroy {
    saidas: ISaida[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected saidaService: SaidaService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.saidaService
            .query()
            .pipe(
                filter((res: HttpResponse<ISaida[]>) => res.ok),
                map((res: HttpResponse<ISaida[]>) => res.body)
            )
            .subscribe(
                (res: ISaida[]) => {
                    this.saidas = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSaidas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISaida) {
        return item.id;
    }

    registerChangeInSaidas() {
        this.eventSubscriber = this.eventManager.subscribe('saidaListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
