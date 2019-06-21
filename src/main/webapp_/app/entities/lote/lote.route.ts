import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Lote } from 'app/shared/model/lote.model';
import { LoteService } from './lote.service';
import { LoteComponent } from './lote.component';
import { LoteDetailComponent } from './lote-detail.component';
import { LoteUpdateComponent } from './lote-update.component';
import { LoteDeletePopupComponent } from './lote-delete-dialog.component';
import { ILote } from 'app/shared/model/lote.model';

@Injectable({ providedIn: 'root' })
export class LoteResolve implements Resolve<ILote> {
    constructor(private service: LoteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILote> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Lote>) => response.ok),
                map((lote: HttpResponse<Lote>) => lote.body)
            );
        }
        return of(new Lote());
    }
}

export const loteRoute: Routes = [
    {
        path: '',
        component: LoteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'porkaLightStockDataBaseApp.lote.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LoteDetailComponent,
        resolve: {
            lote: LoteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'porkaLightStockDataBaseApp.lote.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LoteUpdateComponent,
        resolve: {
            lote: LoteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'porkaLightStockDataBaseApp.lote.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LoteUpdateComponent,
        resolve: {
            lote: LoteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'porkaLightStockDataBaseApp.lote.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lotePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: LoteDeletePopupComponent,
        resolve: {
            lote: LoteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'porkaLightStockDataBaseApp.lote.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
