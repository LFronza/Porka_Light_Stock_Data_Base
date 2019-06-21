import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILote } from 'app/shared/model/lote.model';
import { AccountService } from 'app/core';
import { LoteService } from './lote.service';

@Component({
    selector: 'jhi-lote',
    templateUrl: './lote.component.html'
})
export class LoteComponent implements OnInit, OnDestroy {
    lotes: ILote[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected loteService: LoteService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.loteService
            .query()
            .pipe(
                filter((res: HttpResponse<ILote[]>) => res.ok),
                map((res: HttpResponse<ILote[]>) => res.body)
            )
            .subscribe(
                (res: ILote[]) => {
                    this.lotes = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLotes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILote) {
        return item.id;
    }

    registerChangeInLotes() {
        this.eventSubscriber = this.eventManager.subscribe('loteListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
