/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PorkaLightStockDataBaseTestModule } from '../../../test.module';
import { ApresentacaoUpdateComponent } from 'app/entities/apresentacao/apresentacao-update.component';
import { ApresentacaoService } from 'app/entities/apresentacao/apresentacao.service';
import { Apresentacao } from 'app/shared/model/apresentacao.model';

describe('Component Tests', () => {
    describe('Apresentacao Management Update Component', () => {
        let comp: ApresentacaoUpdateComponent;
        let fixture: ComponentFixture<ApresentacaoUpdateComponent>;
        let service: ApresentacaoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PorkaLightStockDataBaseTestModule],
                declarations: [ApresentacaoUpdateComponent]
            })
                .overrideTemplate(ApresentacaoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ApresentacaoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApresentacaoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Apresentacao(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.apresentacao = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Apresentacao();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.apresentacao = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
