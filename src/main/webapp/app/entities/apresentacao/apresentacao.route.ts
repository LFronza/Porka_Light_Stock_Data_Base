import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Apresentacao } from 'app/shared/model/apresentacao.model';
import { ApresentacaoService } from './apresentacao.service';
import { ApresentacaoComponent } from './apresentacao.component';
import { ApresentacaoDetailComponent } from './apresentacao-detail.component';
import { ApresentacaoUpdateComponent } from './apresentacao-update.component';
import { ApresentacaoDeletePopupComponent } from './apresentacao-delete-dialog.component';
import { IApresentacao } from 'app/shared/model/apresentacao.model';

@Injectable({ providedIn: 'root' })
export class ApresentacaoResolve implements Resolve<IApresentacao> {
    constructor(private service: ApresentacaoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IApresentacao> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Apresentacao>) => response.ok),
                map((apresentacao: HttpResponse<Apresentacao>) => apresentacao.body)
            );
        }
        return of(new Apresentacao());
    }
}

export const apresentacaoRoute: Routes = [
    {
        path: '',
        component: ApresentacaoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'porkaLightStockDataBaseApp.apresentacao.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ApresentacaoDetailComponent,
        resolve: {
            apresentacao: ApresentacaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'porkaLightStockDataBaseApp.apresentacao.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ApresentacaoUpdateComponent,
        resolve: {
            apresentacao: ApresentacaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'porkaLightStockDataBaseApp.apresentacao.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ApresentacaoUpdateComponent,
        resolve: {
            apresentacao: ApresentacaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'porkaLightStockDataBaseApp.apresentacao.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const apresentacaoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ApresentacaoDeletePopupComponent,
        resolve: {
            apresentacao: ApresentacaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'porkaLightStockDataBaseApp.apresentacao.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
