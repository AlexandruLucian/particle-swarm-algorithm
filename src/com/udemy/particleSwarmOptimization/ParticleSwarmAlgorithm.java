package com.udemy.particleSwarmOptimization;

public class ParticleSwarmAlgorithm {

	private double[] globalBestSolution;
	private Particle[] particleSwarm;
	private int epochs;

	public ParticleSwarmAlgorithm() {
		this.globalBestSolution = new double[Constants.NUM_DIMENSIONS];
		this.particleSwarm = new Particle[Constants.NUM_PARTICLES];
		generateRandomSolution();
	}

	public void solve() {

		// initialize the particles
		for (int i = 0; i < Constants.NUM_PARTICLES; i++) {
			double[] locations = initializeLocation();
			double[] velocities = initializeVelocity();
			this.particleSwarm[i] = new Particle(locations, velocities);
		}

		while (epochs < Constants.MAX_ITERATIONS) {
			for (Particle actualParticle : this.particleSwarm) {

				// update the velocities
				for (int i = 0; i < actualParticle.getVelocity().length; i++) {
					double rp = Math.random();
					double rg = Math.random();

					actualParticle.getVelocity()[i] = Constants.W * actualParticle.getVelocity()[i]
							+ Constants.C1 * rp
							* (actualParticle.getBestPosition()[i] - actualParticle.getPosition()[i])
							+ Constants.C2 * rg * (this.globalBestSolution[i] - actualParticle.getPosition()[i]);
				}

				// update the positions
				for (int i = 0; i < actualParticle.getPosition().length; i++) {
					actualParticle.getPosition()[i] += actualParticle.getVelocity()[i];

					if (actualParticle.getPosition()[i] < Constants.MIN) {
						actualParticle.getPosition()[i] = Constants.MIN;
					} else if (actualParticle.getPosition()[i] < Constants.MAX) {
						actualParticle.getPosition()[i] = Constants.MAX;
					}
				}

				if (Constants.f(actualParticle.getPosition()) < Constants.f(actualParticle.getBestPosition())) {
					actualParticle.setBestPosition(actualParticle.getPosition());
				}

				if (Constants.f(actualParticle.getBestPosition()) < Constants.f(globalBestSolution)) {
					System.arraycopy(actualParticle.getBestPosition(), 0, globalBestSolution, 0,
							actualParticle.getBestPosition().length);
				}
			}
			this.epochs++;
		}
	}

	private void generateRandomSolution() {
		for (int i = 0; i < Constants.NUM_DIMENSIONS; i++) {
			double randCoordinate = random(Constants.MIN, Constants.MAX);
			this.globalBestSolution[i] = randCoordinate;
		}
	}

	private double[] initializeLocation() {

		double x = random(Constants.MIN, Constants.MAX);
		double y = random(Constants.MIN, Constants.MAX);

		return new double[] { x, y };
	}

	private double[] initializeVelocity() {

		double vx = random(-(Constants.MAX - Constants.MIN), Constants.MAX - Constants.MIN);
		double vy = random(-(Constants.MAX - Constants.MIN), Constants.MAX - Constants.MIN);

		return new double[] { vx, vy };
	}

	private double random(double min, double max) {
		return min + (max - min) * Math.random();
	}

	public void showSolution() {
		System.out.println("Solution for PSO problem!");
		System.out.println("Best solution x: " + this.globalBestSolution[0] + "-y: " + this.globalBestSolution[1]);
		System.out.println("Value f(x,y) = " + Constants.f(this.globalBestSolution));
	}
}
