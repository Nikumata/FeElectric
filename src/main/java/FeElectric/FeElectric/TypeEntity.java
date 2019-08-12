//package FeElectric.FeElectric;
//
//public class TypeEntity {
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}
//
//	public static void train() {
//		// train the name finder
//		String typedEntities = "<START:organization> NATO <END>\n" + "<START:location> United States <END>\n"
//				+ "<START:organization> NATO Parliamentary Assembly <END>\n" + "<START:location> Edinburgh <END>\n"
//				+ "<START:location> Britain <END>\n" + "<START:person> Anders Fogh Rasmussen <END>\n" + "<START:location> U . S . <END>\n"
//				+ "<START:person> Barack Obama <END>\n" + "<START:location> Afghanistan <END>\n" + "<START:person> Rasmussen <END>\n"
//				+ "<START:location> Afghanistan <END>\n" + "<START:date> 2010 <END>";
//		ObjectStream<NameSample> sampleStream = new NameSampleDataStream(
//				new PlainTextByLineStream(new MockInputStreamFactory(typedEntities), "UTF-8"));
//
//		TrainingParameters params = new TrainingParameters();
//		params.put(TrainingParameters.ALGORITHM_PARAM, "MAXENT");
//		params.put(TrainingParameters.ITERATIONS_PARAM, 70);
//		params.put(TrainingParameters.CUTOFF_PARAM, 1);
//
//		TokenNameFinderModel nameFinderModel = NameFinderME.train("eng", null, sampleStream, params,
//				TokenNameFinderFactory.create(null, null, Collections.emptyMap(), new BioCodec()));
//	}
//
//}
