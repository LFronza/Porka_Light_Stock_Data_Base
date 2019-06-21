/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PorkaLightStockDataBaseTestModule } from '../../../test.module';
import { SaidaUpdateComponent } from 'app/entities/saida/saida-update.component';
import { SaidaService } from 'app/entities/saida/saida.service';
import { Saida } from 'app/shared/model/saida.model';

describe('Component Tests', () => {
    describe('Saida Management Update Component', () => {
        let comp: SaidaUpdateComponent;
        let fixture: ComponentFixture<SaidaUpdateComponent>;
        let service: SaidaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PorkaLightStockDataBaseTestModule],
                declarations: [SaidaUpdateComponent]
            })
                .overrideTemplate(SaidaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SaidaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaidaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Saida(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.saida = entity;
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
                    const entity = new Saida();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.saida = entity;
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
