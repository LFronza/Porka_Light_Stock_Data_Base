import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'produto',
                loadChildren: './produto/produto.module#PorkaLightStockDataBaseProdutoModule'
            },
            {
                path: 'saida',
                loadChildren: './saida/saida.module#PorkaLightStockDataBaseSaidaModule'
            },
            {
                path: 'apresentacao',
                loadChildren: './apresentacao/apresentacao.module#PorkaLightStockDataBaseApresentacaoModule'
            },
            {
                path: 'grupo',
                loadChildren: './grupo/grupo.module#PorkaLightStockDataBaseGrupoModule'
            },
            {
                path: 'lote',
                loadChildren: './lote/lote.module#PorkaLightStockDataBaseLoteModule'
            },
            {
                path: 'fornecedor',
                loadChildren: './fornecedor/fornecedor.module#PorkaLightStockDataBaseFornecedorModule'
            },
            {
                path: 'produto',
                loadChildren: './produto/produto.module#PorkaLightStockDataBaseProdutoModule'
            },
            {
                path: 'saida',
                loadChildren: './saida/saida.module#PorkaLightStockDataBaseSaidaModule'
            },
            {
                path: 'apresentacao',
                loadChildren: './apresentacao/apresentacao.module#PorkaLightStockDataBaseApresentacaoModule'
            },
            {
                path: 'grupo',
                loadChildren: './grupo/grupo.module#PorkaLightStockDataBaseGrupoModule'
            },
            {
                path: 'lote',
                loadChildren: './lote/lote.module#PorkaLightStockDataBaseLoteModule'
            },
            {
                path: 'fornecedor',
                loadChildren: './fornecedor/fornecedor.module#PorkaLightStockDataBaseFornecedorModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PorkaLightStockDataBaseEntityModule {}
