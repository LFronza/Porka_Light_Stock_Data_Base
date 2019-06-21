/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PorkaLightStockDataBaseTestModule } from '../../../test.module';
import { SaidaDetailComponent } from 'app/entities/saida/saida-detail.component';
import { Saida } from 'app/shared/model/saida.model';

describe('Component Tests', () => {
    describe('Saida Management Detail Component', () => {
        let comp: SaidaDetailComponent;
        let fixture: ComponentFixture<SaidaDetailComponent>;
        const route = ({ data: of({ saida: new Saida(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PorkaLightStockDataBaseTestModule],
                declarations: [SaidaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SaidaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SaidaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.saida).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
