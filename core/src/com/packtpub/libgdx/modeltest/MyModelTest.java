package com.packtpub.libgdx.modeltest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Array;

public class MyModelTest extends ApplicationAdapter {
	public Environment environment;
	public PerspectiveCamera cam;
	public CameraInputController camController;
	public ModelBatch modelBatch;
	public Model model;
	public ModelInstance instance;
	public AssetManager assets;
	public Array<ModelInstance> instances = new Array<ModelInstance>();

	@Override
	public void create() {
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
		modelBatch = new ModelBatch();
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(5, 20, 20);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 100f;
		cam.update();

		assets = new AssetManager();
		assets.load("car.g3dj", Model.class);
		assets.finishLoading();
		model = assets.get("car.g3dj", Model.class);
		for(float x = -30; x <= 10f; x += 20) {
			for(float z = -30f; z <= 0f; z += 10f) {
				ModelInstance instance = new ModelInstance(model);
				instance.transform.setToTranslation(x, 0, z);
				instances.add(instance);
			}
		}

		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);
		// camController.update();

		//ModelBuilder modelBuilder = new ModelBuilder();
		//model = modelBuilder.createSphere(2, 2, 2, 20, 20, new Material(ColorAttribute.createDiffuse(Color.YELLOW)), Usage.Position | Usage.Normal);
		//instance = new ModelInstance(model);

		//camController = new CameraInputController(cam);

		//Gdx.input.setInputProcessor(camController);
	}

	@Override
	public void render() {
		camController.update();

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(cam);
		for (ModelInstance instance : instances) {
            modelBatch.render(instance, environment);
        }
		modelBatch.end();
	}

	@Override
	public void dispose() {
		modelBatch.dispose();
		model.dispose();
	}
}