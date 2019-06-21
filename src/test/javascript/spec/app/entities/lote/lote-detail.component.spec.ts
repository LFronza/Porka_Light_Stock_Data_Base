/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PorkaLightStockDataBaseTestModule } from '../../../test.module';
import { LoteDetailComponent } from 'app/entities/lote/lote-detail.component';
import { Lote } from 'app/shared/model/lote.model';

describe('Component Tests', () => {
    describe('Lote Management Detail Component', () => {
        let comp: LoteDetailComponent;
        let fixture: ComponentFixture<LoteDetailComponent>;
        const route = ({ data: of({ lote: new Lote(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PorkaLightStockDataBaseTestModule],
                declarations: [LoteDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LoteDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LoteDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.lote).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
