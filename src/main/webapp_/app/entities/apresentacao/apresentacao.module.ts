import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PorkaLightStockDataBaseSharedModule } from 'app/shared';
import {
    ApresentacaoComponent,
    ApresentacaoDetailComponent,
    ApresentacaoUpdateComponent,
    ApresentacaoDeletePopupComponent,
    ApresentacaoDeleteDialogComponent,
    apresentacaoRoute,
    apresentacaoPopupRoute
} from './';

const ENTITY_STATES = [...apresentacaoRoute, ...apresentacaoPopupRoute];

@NgModule({
    imports: [PorkaLightStockDataBaseSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ApresentacaoComponent,
        ApresentacaoDetailComponent,
        ApresentacaoUpdateComponent,
        ApresentacaoDeleteDialogComponent,
        ApresentacaoDeletePopupComponent
    ],
    entryComponents: [
        ApresentacaoComponent,
        ApresentacaoUpdateComponent,
        ApresentacaoDeleteDialogComponent,
        ApresentacaoDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PorkaLightStockDataBaseApresentacaoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
