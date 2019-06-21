/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PorkaLightStockDataBaseTestModule } from '../../../test.module';
import { FornecedorComponent } from 'app/entities/fornecedor/fornecedor.component';
import { FornecedorService } from 'app/entities/fornecedor/fornecedor.service';
import { Fornecedor } from 'app/shared/model/fornecedor.model';

describe('Component Tests', () => {
    describe('Fornecedor Management Component', () => {
        let comp: FornecedorComponent;
        let fixture: ComponentFixture<FornecedorComponent>;
        let service: FornecedorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PorkaLightStockDataBaseTestModule],
                declarations: [FornecedorComponent],
                providers: []
            })
                .overrideTemplate(FornecedorComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FornecedorComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FornecedorService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Fornecedor(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.fornecedors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
