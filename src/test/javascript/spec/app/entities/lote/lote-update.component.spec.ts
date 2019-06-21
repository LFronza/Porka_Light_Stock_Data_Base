/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PorkaLightStockDataBaseTestModule } from '../../../test.module';
import { LoteUpdateComponent } from 'app/entities/lote/lote-update.component';
import { LoteService } from 'app/entities/lote/lote.service';
import { Lote } from 'app/shared/model/lote.model';

describe('Component Tests', () => {
    describe('Lote Management Update Component', () => {
        let comp: LoteUpdateComponent;
        let fixture: ComponentFixture<LoteUpdateComponent>;
        let service: LoteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PorkaLightStockDataBaseTestModule],
                declarations: [LoteUpdateComponent]
            })
                .overrideTemplate(LoteUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LoteUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LoteService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Lote(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.lote = entity;
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
                    const entity = new Lote();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.lote = entity;
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
