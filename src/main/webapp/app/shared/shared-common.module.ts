import { NgModule } from '@angular/core';

import { PorkaLightStockDataBaseSharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [PorkaLightStockDataBaseSharedLibsModule],
    declarations: [FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent],
    exports: [PorkaLightStockDataBaseSharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent]
})
export class PorkaLightStockDataBaseSharedCommonModule {}
