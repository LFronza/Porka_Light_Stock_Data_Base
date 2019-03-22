import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import {
    PorkaLightStockDataBaseSharedLibsModule,
    PorkaLightStockDataBaseSharedCommonModule,
    JhiLoginModalComponent,
    HasAnyAuthorityDirective
} from './';

@NgModule({
    imports: [PorkaLightStockDataBaseSharedLibsModule, PorkaLightStockDataBaseSharedCommonModule],
    declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [JhiLoginModalComponent],
    exports: [PorkaLightStockDataBaseSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PorkaLightStockDataBaseSharedModule {
    static forRoot() {
        return {
            ngModule: PorkaLightStockDataBaseSharedModule
        };
    }
}
