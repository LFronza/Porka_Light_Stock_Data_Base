import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFornecedor } from 'app/shared/model/fornecedor.model';
import { AccountService } from 'app/core';
import { FornecedorService } from './fornecedor.service';

@Component({
    selector: 'jhi-fornecedor',
    templateUrl: './fornecedor.component.html'
})
export class FornecedorComponent implements OnInit, OnDestroy {
    fornecedors: IFornecedor[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected fornecedorService: FornecedorService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.fornecedorService
            .query()
            .pipe(
                filter((res: HttpResponse<IFornecedor[]>) => res.ok),
                map((res: HttpResponse<IFornecedor[]>) => res.body)
            )
            .subscribe(
                (res: IFornecedor[]) => {
                    this.fornecedors = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFornecedors();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFornecedor) {
        return item.id;
    }

    registerChangeInFornecedors() {
        this.eventSubscriber = this.eventManager.subscribe('fornecedorListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
