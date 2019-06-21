/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PorkaLightStockDataBaseTestModule } from '../../../test.module';
import { GrupoUpdateComponent } from 'app/entities/grupo/grupo-update.component';
import { GrupoService } from 'app/entities/grupo/grupo.service';
import { Grupo } from 'app/shared/model/grupo.model';

describe('Component Tests', () => {
    describe('Grupo Management Update Component', () => {
        let comp: GrupoUpdateComponent;
        let fixture: ComponentFixture<GrupoUpdateComponent>;
        let service: GrupoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PorkaLightStockDataBaseTestModule],
                declarations: [GrupoUpdateComponent]
            })
                .overrideTemplate(GrupoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GrupoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrupoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Grupo(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.grupo = entity;
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
                    const entity = new Grupo();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.grupo = entity;
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
