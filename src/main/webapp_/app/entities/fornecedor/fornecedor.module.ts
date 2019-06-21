import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PorkaLightStockDataBaseSharedModule } from 'app/shared';
import {
    FornecedorComponent,
    FornecedorDetailComponent,
    FornecedorUpdateComponent,
    FornecedorDeletePopupComponent,
    FornecedorDeleteDialogComponent,
    fornecedorRoute,
    fornecedorPopupRoute
} from './';

const ENTITY_STATES = [...fornecedorRoute, ...fornecedorPopupRoute];

@NgModule({
    imports: [PorkaLightStockDataBaseSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FornecedorComponent,
        FornecedorDetailComponent,
        FornecedorUpdateComponent,
        FornecedorDeleteDialogComponent,
        FornecedorDeletePopupComponent
    ],
    entryComponents: [FornecedorComponent, FornecedorUpdateComponent, FornecedorDeleteDialogComponent, FornecedorDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PorkaLightStockDataBaseFornecedorModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
