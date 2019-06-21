/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PorkaLightStockDataBaseTestModule } from '../../../test.module';
import { ApresentacaoComponent } from 'app/entities/apresentacao/apresentacao.component';
import { ApresentacaoService } from 'app/entities/apresentacao/apresentacao.service';
import { Apresentacao } from 'app/shared/model/apresentacao.model';

describe('Component Tests', () => {
    describe('Apresentacao Management Component', () => {
        let comp: ApresentacaoComponent;
        let fixture: ComponentFixture<ApresentacaoComponent>;
        let service: ApresentacaoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PorkaLightStockDataBaseTestModule],
                declarations: [ApresentacaoComponent],
                providers: []
            })
                .overrideTemplate(ApresentacaoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ApresentacaoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApresentacaoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Apresentacao(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.apresentacaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
