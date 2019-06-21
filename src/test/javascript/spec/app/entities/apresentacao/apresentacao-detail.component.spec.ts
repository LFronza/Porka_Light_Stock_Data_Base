/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PorkaLightStockDataBaseTestModule } from '../../../test.module';
import { ApresentacaoDetailComponent } from 'app/entities/apresentacao/apresentacao-detail.component';
import { Apresentacao } from 'app/shared/model/apresentacao.model';

describe('Component Tests', () => {
    describe('Apresentacao Management Detail Component', () => {
        let comp: ApresentacaoDetailComponent;
        let fixture: ComponentFixture<ApresentacaoDetailComponent>;
        const route = ({ data: of({ apresentacao: new Apresentacao(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PorkaLightStockDataBaseTestModule],
                declarations: [ApresentacaoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ApresentacaoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApresentacaoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.apresentacao).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
