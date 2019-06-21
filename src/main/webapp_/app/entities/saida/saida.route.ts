import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Saida } from 'app/shared/model/saida.model';
import { SaidaService } from './saida.service';
import { SaidaComponent } from './saida.component';
import { SaidaDetailComponent } from './saida-detail.component';
import { SaidaUpdateComponent } from './saida-update.component';
import { SaidaDeletePopupComponent } from './saida-delete-dialog.component';
import { ISaida } from 'app/shared/model/saida.model';

@Injectable({ providedIn: 'root' })
export class SaidaResolve implements Resolve<ISaida> {
    constructor(private service: SaidaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISaida> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Saida>) => response.ok),
                map((saida: HttpResponse<Saida>) => saida.body)
            );
        }
        return of(new Saida());
    }
}

export const saidaRoute: Routes = [
    {
        path: '',
        component: SaidaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'porkaLightStockDataBaseApp.saida.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SaidaDetailComponent,
        resolve: {
            saida: SaidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'porkaLightStockDataBaseApp.saida.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SaidaUpdateComponent,
        resolve: {
            saida: SaidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'porkaLightStockDataBaseApp.saida.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SaidaUpdateComponent,
        resolve: {
            saida: SaidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'porkaLightStockDataBaseApp.saida.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const saidaPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SaidaDeletePopupComponent,
        resolve: {
            saida: SaidaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'porkaLightStockDataBaseApp.saida.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
