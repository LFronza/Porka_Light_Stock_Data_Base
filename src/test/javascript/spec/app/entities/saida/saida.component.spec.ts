/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PorkaLightStockDataBaseTestModule } from '../../../test.module';
import { SaidaComponent } from 'app/entities/saida/saida.component';
import { SaidaService } from 'app/entities/saida/saida.service';
import { Saida } from 'app/shared/model/saida.model';

describe('Component Tests', () => {
    describe('Saida Management Component', () => {
        let comp: SaidaComponent;
        let fixture: ComponentFixture<SaidaComponent>;
        let service: SaidaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PorkaLightStockDataBaseTestModule],
                declarations: [SaidaComponent],
                providers: []
            })
                .overrideTemplate(SaidaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SaidaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaidaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Saida(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.saidas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
