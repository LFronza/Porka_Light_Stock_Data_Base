import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PorkaLightStockDataBaseSharedModule } from 'app/shared';
import {
    SaidaComponent,
    SaidaDetailComponent,
    SaidaUpdateComponent,
    SaidaDeletePopupComponent,
    SaidaDeleteDialogComponent,
    saidaRoute,
    saidaPopupRoute
} from './';

const ENTITY_STATES = [...saidaRoute, ...saidaPopupRoute];

@NgModule({
    imports: [PorkaLightStockDataBaseSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SaidaComponent, SaidaDetailComponent, SaidaUpdateComponent, SaidaDeleteDialogComponent, SaidaDeletePopupComponent],
    entryComponents: [SaidaComponent, SaidaUpdateComponent, SaidaDeleteDialogComponent, SaidaDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PorkaLightStockDataBaseSaidaModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
