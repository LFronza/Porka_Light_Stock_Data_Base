import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PorkaLightStockDataBaseSharedModule } from 'app/shared';
import {
    GrupoComponent,
    GrupoDetailComponent,
    GrupoUpdateComponent,
    GrupoDeletePopupComponent,
    GrupoDeleteDialogComponent,
    grupoRoute,
    grupoPopupRoute
} from './';

const ENTITY_STATES = [...grupoRoute, ...grupoPopupRoute];

@NgModule({
    imports: [PorkaLightStockDataBaseSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [GrupoComponent, GrupoDetailComponent, GrupoUpdateComponent, GrupoDeleteDialogComponent, GrupoDeletePopupComponent],
    entryComponents: [GrupoComponent, GrupoUpdateComponent, GrupoDeleteDialogComponent, GrupoDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PorkaLightStockDataBaseGrupoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
