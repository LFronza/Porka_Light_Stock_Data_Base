/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PorkaLightStockDataBaseTestModule } from '../../../test.module';
import { LoteComponent } from 'app/entities/lote/lote.component';
import { LoteService } from 'app/entities/lote/lote.service';
import { Lote } from 'app/shared/model/lote.model';

describe('Component Tests', () => {
    describe('Lote Management Component', () => {
        let comp: LoteComponent;
        let fixture: ComponentFixture<LoteComponent>;
        let service: LoteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PorkaLightStockDataBaseTestModule],
                declarations: [LoteComponent],
                providers: []
            })
                .overrideTemplate(LoteComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LoteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LoteService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Lote(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.lotes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
